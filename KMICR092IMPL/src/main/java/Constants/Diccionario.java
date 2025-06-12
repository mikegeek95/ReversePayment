package Constants;

public class Diccionario {

    public static final String CODIGO_MOVIMIENTO = "codigo_movimiento";

    public static final String ACTCARAU = "ACTCARAU";
    public static final String ANPGANTC = "ANPGANTC";
    public static final String ANPGANTD = "ANPGANTD";
    public static final String ANPGATCD = "ANPGATCD";
    public static final String ANPGATCP = "ANPGATCP";
    public static final String ANPGATIC = "ANPGATIC";
    public static final String ANPGCMDI = "ANPGCMDI";
    public static final String ANPGGSCB = "ANPGGSCB";
    public static final String ANPGIVGC = "ANPGIVGC";
    public static final String ANPGMNCA = "ANPGMNCA";
    public static final String ANPGMNCD = "ANPGMNCD";
    public static final String ANPGMNCN = "ANPGMNCN";
    public static final String ANPGMNIC = "ANPGMNIC";
    public static final String ANPGVNCN = "ANPGVNCN";
    public static final String ANPGVNCP = "ANPGVNCP";
    public static final String ANPGVNDI = "ANPGVNDI";
    public static final String ANPRCCON = "ANPRCCON";
    public static final String ANPRCDIS = "ANPRCDIS";
    public static final String ANULDCOM = "ANULDCOM";
    public static final String ANULDIVA = "ANULDIVA";
    public static final String ANULDISP = "ANULDISP";
    public static final String ANULRETN = "ANULRETN";
    public static final String BLOQCONT = "BLOQCONT";
    public static final String CAMCUVIN = "CAMCUVIN";
    public static final String CANCONTR = "CANCONTR";
    public static final String CRECOMEXD = "CRECOMEXD";
    public static final String CRECOMEXH = "CRECOMEXH";
    public static final String CRECOMVGD = "CRECOMVGD";
    public static final String CRECOMVGH = "CRECOMVGH";
    public static final String CREIVAEXD = "CREIVAEXD";
    public static final String CREIVAEXH = "CREIVAEXH";
    public static final String CREIVAVGD = "CREIVAVGD";
    public static final String CREIVAVGH = "CREIVAVGH";
    public static final String CRETRAEXD = "CRETRAEXD";
    public static final String CRETRAEXH = "CRETRAEXH";
    public static final String CRETRAVGD = "CRETRAVGD";
    public static final String CRETRAVGH = "CRETRAVGH";
    public static final String DESCARAU = "DESCARAU";
    public static final String DISPOCOM = "DISPOCOM";
    public static final String DISPOIVA = "DISPOIVA";
    public static final String DISPOSIC = "DISPOSIC";
    public static final String DSBLOQCN = "DSBLOQCN";
    public static final String DVCOMISI = "DVCOMISI";
    public static final String FORMCONS = "FORMCONS";
    public static final String GCIVACOB = "GCIVACOB";
    public static final String GCGTOCOB = "GCGTOCOB";
    public static final String LQDVCIV = "LQDVCIV";
    public static final String LQDVCCA = "LQDVCCA";
    public static final String LQDVCCO = "LQDVCCO";
    public static final String LQDVPCA = "LQDVPCA";
    public static final String LQDVPCO = "LQDVPCO";
    public static final String LQDVPIV = "LQDVPIV";
    public static final String PAGMENCA = "PAGMENCA";
    public static final String PAGMENCN = "PAGMENCN";
    public static final String PAGMENDI = "PAGMENDI";
    public static final String PGAUTCON = "PGAUTCON";
    public static final String PGANTCAP = "PGANTCAP";
    public static final String PGANTCON = "PGANTCON";
    public static final String PGANTDIS = "PGANTDIS";
    public static final String PGCOMDIS = "PGCOMDIS";
    public static final String PGGASCOB = "PGGASCOB";
    public static final String PGIVAGCB = "PGIVAGCB";
    public static final String PGMNCMDI = "PGMNCMDI";
    public static final String PGMNIVAC = "PGMNIVAC";
    public static final String PGRECCON = "PGRECCON";
    public static final String PGRECDIS = "PGRECDIS";
    public static final String PGVENCON = "PGVENCON";
    public static final String PGVENCAP = "PGVENCAP";
    public static final String PGVNCDIS = "PGVNCDIS";
    public static final String PGVNIVAC = "PGVNIVAC";
    public static final String PIVACOMD = "PIVACOMD";
    public static final String REFCOMDI = "REFCOMDI";
    public static final String REFCOIVA = "REFCOIVA";
    public static final String REMACAP = "REMACAP";
    public static final String REMACOM = "REMACOM";
    public static final String REMAIVAC = "REMAIVAC";
    public static final String TVVCOMVGD = "TVVCOMVGD";
    public static final String TVVCOMVGH = "TVVCOMVGH";
    public static final String TVVIVAVGD = "TVVIVAVGD";
    public static final String TVVIVAVGH = "TVVIVAVGH";
    public static final String TVVTRAVGD = "TVVTRAVGD";
    public static final String TVVTRAVGH = "TVVTRAVGH";

    public static String obtenerCodigoContrario(String codigoMovimiento) {
        switch (codigoMovimiento) {
            case DISPOSIC:    return ANULDISP;
            case DISPOCOM:    return ANULDCOM;
            case DISPOIVA:    return ANULDIVA;
            case PAGMENCN:    return ANPGMNCN;
            case PAGMENCA:    return ANPGMNCA;
            case PGMNCMDI:    return ANPGMNCD;
            case PGMNIVAC:    return ANPGMNIC;
            case PGANTCON:    return ANPGANTC;
            case PGANTDIS:    return ANPGANTD;
            case PGANTCAP:    return ANPGATCP;
            case PGCOMDIS:    return ANPGATCD;
            case PIVACOMD:    return ANPGATIC;
            case PGGASCOB:    return ANPGGSCB;
            case PGIVAGCB:    return ANPGIVGC;
            case PGVENCON:    return ANPGVNCN;
            case PGVENCAP:    return ANPGVNCP;
            case PGVNCDIS:    return ANPGCMDI;
            case PGVNIVAC:    return ANPGVNDI;
            case ACTCARAU:    return DESCARAU;
            case BLOQCONT:    return DSBLOQCN;
            case PGRECCON:    return ANPRCCON;
            case PGRECDIS:    return ANPRCDIS;
            case GCGTOCOB:    return ANPGGSCB; // efecto indirecto
            case GCIVACOB:    return ANPGIVGC; // efecto indirecto
            case CRETRAVGD:   return CRETRAVGH;
            case CRETRAEXD:   return CRETRAEXH;
            case CRECOMVGD:   return CRECOMVGH;
            case CRECOMEXD:   return CRECOMEXH;
            case CREIVAVGD:   return CREIVAVGH;
            case CREIVAEXD:   return CREIVAEXH;
            case TVVTRAVGD:   return TVVTRAVGH;
            case TVVCOMVGD:   return TVVCOMVGH;
            case TVVIVAVGD:   return TVVIVAVGH;
            default:          return null; // o "" si se desea devolver cadena vacía
        }
    }
}