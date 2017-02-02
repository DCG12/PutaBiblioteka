import java.util.Date;

/**
 * Created by 46406163y on 30/01/17.
 */
public class Prestec {

    private int id;
    private int llibre_id;
    private int soci_id;
    private String llibre;
    private String soci;
    private Date data_inici;
    private Date data_final;

    public Prestec() {}

    public Prestec(int llibre_id, int soci_id, String llibre, String soci, Date data_inici, Date data_final) {
        this.id = id;
        this.llibre_id = llibre_id;
        this.soci_id = soci_id;
        this.llibre = llibre;
        this.soci = soci;
        this.data_inici = data_inici;
        this.data_final = data_final;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLlibre_id() {
        return llibre_id;
    }

    public void setLlibre_id(int llibre_id) {
        this.llibre_id = llibre_id;
    }

    public int getSoci_id() {
        return soci_id;
    }

    public void setSoci_id(int soci_id) {
        this.soci_id = soci_id;
    }

    public String getLlibre() {
        return llibre;
    }

    public void setLlibre(String llibre) {
        this.llibre = llibre;
    }

    public String getSoci() {
        return soci;
    }

    public void setSoci(String soci) {
        this.soci = soci;
    }

    public Date getData_inici() {
        return data_inici;
    }

    public void setData_inici(Date data_inici) {
        this.data_inici = data_inici;
    }

    public Date getData_final() {
        return data_final;
    }

    public void setData_final(Date data_final) {
        this.data_final = data_final;
    }
}
