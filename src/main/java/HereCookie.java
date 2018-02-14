class HereCookie {
    private static HereCookie ourInstance = new HereCookie();
    private String value = "";
    private String name = "";

    static HereCookie getInstance() {
        return ourInstance;
    }

    /**
     * get the current cookie value
     *
     * @return the current cookie value, must be setted up manually
     */
    String getValue() {
        return value;
    }

    String getName() {
        return name;
    }

}
