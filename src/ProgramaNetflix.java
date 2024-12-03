// Bruno Lauand Ferrão - 32217994

public class ProgramaNetflix {
    private String id;
    private String titulo;
    private String showType;
    private String descricao;
    private int releaseYear;
    private String ageCertification;
    private int runtime;
    private String generos;
    private String productionCountries;
    private double temporadas;
    private String imdbId;
    private double imdbScore;
    private double imdbVotes;
    private double tmdbPopularity;
    private double tmdbScore;

    public ProgramaNetflix(String id, String titulo, String showType, String descricao, int releaseYear, String ageCertification, int runtime, String generos, String productionCountries,
                            double temporadas, String imdbId, double imdbScore, double imdbVotes, double tmdbPopularity, double tmdbScore) {
        this.id = id;
        this.titulo = titulo;
        this.showType = showType;
        this.descricao = descricao;
        this.releaseYear = releaseYear;
        this.ageCertification = ageCertification;
        this.runtime = runtime;
        this.generos = generos;
        this.productionCountries = productionCountries;
        this.temporadas = temporadas;
        this.imdbId = imdbId;
        this.imdbScore = imdbScore;
        this.imdbVotes = imdbVotes;
        this.tmdbPopularity = tmdbPopularity;
        this.tmdbScore = tmdbScore;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getShowType() {
        return showType;
    }

    public void setShowType(String showType) {
        this.showType = showType;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getAgeCertification() {
        return ageCertification;
    }

    public void setAgeCertification(String ageCertification) {
        this.ageCertification = ageCertification;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public String getGeneros() {
        return generos;
    }

    public void setGeneros(String generos) {
        this.generos = generos;
    }

    public String getProductionCountries() {
        return productionCountries;
    }

    public void setProductionCountries(String productionCountries) {
        this.productionCountries = productionCountries;
    }

    public double getTemporadas() {
        return temporadas;
    }

    public void setTemporadas(double temporadas) {
        this.temporadas = temporadas;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public double getImdbScore() {
        return imdbScore;
    }

    public void setImdbScore(double imdbScore) {
        this.imdbScore = imdbScore;
    }

    public double getImdbVotes() {
        return imdbVotes;
    }

    public void setImdbVotes(double imdbVotes) {
        this.imdbVotes = imdbVotes;
    }

    public double getTmdbPopularity() {
        return tmdbPopularity;
    }

    public void setTmdbPopularity(double tmdbPopularity) {
        this.tmdbPopularity = tmdbPopularity;
    }

    public double getTmdbScore() {
        return tmdbScore;
    }

    public void setTmdbScore(double tmdbScore) {
        this.tmdbScore = tmdbScore;
    }
    
    @Override
    public String toString() {
        return "ID: " + id + " | Título: '" + titulo + "' | Ano de lançamento: " + releaseYear + " | Gêneros: '" + generos + "' | Avaliação IMDB: " + imdbScore;
    }
}
