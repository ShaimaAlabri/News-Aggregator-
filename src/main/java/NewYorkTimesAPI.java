public class NewYorkTimesAPI {
    private static final String API_KEY = "YOUR_API_KEY_HERE";
    private static final String BASE_URL = "https://api.nytimes.com/svc/search/v2/articlesearch.json";

    public static List<Article> searchArticles(String keyword) {
        String url = BASE_URL + "?q=" + keyword + "&api-key=" + API_KEY;
        ObjectMapper mapper = new ObjectMapper();
        List<Article> articles = new ArrayList<>();

        try {
            String json = IOUtils.toString(new URL(url), Charset.forName("UTF-8"));
            JsonNode rootNode = mapper.readTree(json);
            JsonNode docsNode = rootNode.path("response").path("docs");

            for (JsonNode docNode : docsNode) {
                Article article = new Article();
                article.setTitle(docNode.path("headline").path("main").asText());
                article.setAuthor(docNode.path("byline").path("original").asText());
                article.setDate(docNode.path("pub_date").asText());
                article.setCategory(docNode.path("section_name").asText());
                article.setContent(docNode.path("snippet").asText());
                articles.add(article);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return articles;
    }
}
