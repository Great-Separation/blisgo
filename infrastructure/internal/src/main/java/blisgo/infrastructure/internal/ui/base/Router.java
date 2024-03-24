package blisgo.infrastructure.internal.ui.base;

import java.util.StringJoiner;

public class Router {
    protected String routes(Object... strings) {
        StringJoiner sj = new StringJoiner("/", "/", "");

        for (Object str : strings) {
            sj.add(str.toString().toLowerCase());
        }

        return sj.toString();
    }

    protected String fragment(Object target) {
        return "::" + target.toString().toLowerCase();
    }

    protected enum Folder {
        COMMUNITY, DICTIONARY, MEMBER
    }

    protected enum Page {
        INDEX, PROFILE, CATALOGUE, INFO, BOARD, CONTENT, WRITE, EDIT
    }

    public enum Fragment {
        POSTS, DOGAMS, MEMBER, POST, DICTIONARIES, WASTE, WASTES, WASTE_RELATED, REPLIES
    }

}
