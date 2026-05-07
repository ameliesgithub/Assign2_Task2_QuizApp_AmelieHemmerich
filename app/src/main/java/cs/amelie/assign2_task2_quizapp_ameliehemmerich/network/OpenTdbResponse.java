package cs.amelie.assign2_task2_quizapp_ameliehemmerich.network;

import java.util.List;

public class OpenTdbResponse {

    private int response_code;
    private List<QuestionResponse> results;

    public int getResponse_code() {
        return response_code;
    }

    public List<QuestionResponse> getResults() {
        return results;
    }
}
