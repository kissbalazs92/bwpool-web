package utils;

public class ScenarioContext {
    private Object currentPage;

    public void setCurrentPage(Object page) {
        this.currentPage = page;
    }

    public Object getCurrentPage() {
        return currentPage;
    }
}

