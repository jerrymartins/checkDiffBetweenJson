package br.com.checkdiff.usecase;

import br.com.checkdiff.usecase.exception.InvalidJsonException;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class ConvertBase64ToJsonUseCase {

    /**
     * decodes base64 for json
     * @param stringBase64
     * @return
     * @throws InvalidJsonException
     */
    public String execute(String stringBase64) throws InvalidJsonException {
        Gson g = new Gson();
        byte[] decodedBytes = Base64.getDecoder().decode(stringBase64);
        String json = new String(decodedBytes);

        try {
            g.fromJson(json, Object.class);
            return json;
        } catch(com.google.gson.JsonSyntaxException ex) {
            throw new InvalidJsonException("invalid Json");
        }
    }
}
