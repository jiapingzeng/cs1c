package stacks;

/**
 * emulates a web browser by keeping track of current, forward and back links
 */

public class Navigator {

    /**
     * link to current page
     */
    private String currentLink;

    /**
     * list of back links
     */
    private StackList<String> backLinks;

    /**
     * list of forward links
     */
    private StackList<String> forwardLinks;

    /**
     * create a navigator
     */
    public Navigator() {
        backLinks = new StackList<>("Back");
        forwardLinks = new StackList<>("Forward");
    }

    /**
     * set current link, update back links and reset forward links
     * @param link current link
     */
    public void setCurrentLink(String link) {
        if (link == null) return;
        else if (currentLink == null) currentLink = link;
        else {
            String oldLink = currentLink;
            currentLink = link;
            backLinks.push(oldLink);
            forwardLinks.clear();
        }
    }

    /**
     * move back one link
     */
    public void goBack() {
        if (currentLink == null) return;
        forwardLinks.push(currentLink);
        currentLink = backLinks.pop();
    }

    /**
     * move forward one link
     */
    public void goForward() {
        if (currentLink == null) return;
        backLinks.push(currentLink);
        currentLink = forwardLinks.pop();
    }

    /**
     * return current link
     * @return current link
     */
    public String getCurrentLink() {
        return currentLink;
    }

    /**
     * return list of back links
     * @return list of back links
     */
    public StackList<String> getBackLinks() {
        return backLinks;
    }

    /**
     * return list of forward links
     * @return list of forward links
     */
    public StackList<String> getForwardLinks() {
        return forwardLinks;
    }
}
