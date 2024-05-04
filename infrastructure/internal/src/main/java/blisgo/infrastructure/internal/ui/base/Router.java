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

    protected String routesToast() {
        return routes(Folder.BASE, Page.TOAST) + fragment(Fragment.TOAST);
    }

    protected enum Folder {
        COMMUNITY,
        DICTIONARY,
        BASE,
        MEMBER
    }

    protected enum Page {
        INDEX,
        PROFILE,
        CATALOGUE,
        INFO,
        BOARD,
        CONTENT,
        WRITE,
        LAYOUT,
        TOAST,
        EDIT
    }

    public enum Fragment {
        POSTS,
        DOGAMS,
        MEMBER,
        POST,
        DICTIONARIES,
        WASTE,
        WASTES,
        WASTE_RELATED,
        TOAST,
        REPLIES
    }
}
