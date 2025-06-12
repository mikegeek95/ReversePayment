package Constants;

public final class Constants {

    private Constants() {
        // Constructor privado para evitar instanciación
    }

    // Nombres de los properties definidos en el archivo .properties
    public static final String MICRO_CREDIT_CONTRACT = "update.microcredit.contract";
    public static final String AMORTIZATION_CONDITION = "update.amortization.condition";
    public static final String MCECR_AMORTIZATION = "update.mcecr.amortization";

    // Métodos de acceso si deseas mayor legibilidad en el código
    public static String getMicrocreditContractUpdate() {
        return MICRO_CREDIT_CONTRACT;
    }

    public static String getAmortizationConditionUpdate() {
        return AMORTIZATION_CONDITION;
    }

    public static String getMcecrAmortizationUpdate() {
        return MCECR_AMORTIZATION;
    }
}