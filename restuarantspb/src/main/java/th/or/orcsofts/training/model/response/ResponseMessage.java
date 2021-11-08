package th.or.orcsofts.training.model.response;

import lombok.Data;

@Data
public class ResponseMessage {
    public ResponseMessage(String message) {
        this.message = message;
    }

    private String message;
}
