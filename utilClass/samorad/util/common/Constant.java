package samorad.util.common;

public class Constant {

	public static class BackEnd{
		public static class Vario{
			public static final String DELIMITATORE_LINE_FILE = " ";
			public static final Integer MINUS_UNO = -1;
			public static final Double VELOCITA_MINIMA_CAMPIONAMENTO = 60.0;
		}
		public static class CoordinateGeo{
			public static final String NORD = "N";
			public static final String SUD = "S";
			public static final String EST = "E";
			public static final String OVEST = "O";
			public static final String PLUS = "+";
			public static final String MINUS = "-";
			
		}
		
		
		public static class Colour{
			public static final String AZZURRO= "#8BCDFF";
			public static final String VERDE = "#0A8900";
			public static final String GIALLO = "#FFFF00";
			public static final String ARANCIONE = "#FF9955";
			public static final String ROSSO = "#CC0033";
			public static final String VIOLA = "#8100FF";
		}
		
		public static class UnitMis{
			public static final String PERC= "%";
			public static final String PPM = "ppm";
			public static final String CPS = "cps";
		}
		
	}
	
	public static class FrontEnd{
		
		public static class Vario{
			public static final String NON_DEFINITO = "ND";
			public static final String NULL = "null";
			public static final Integer MINUS_UNO = -1;
			public static final Integer NUM_BYTE_APPROSS_LINE = 365;
		}
		
		public static class FileVoloConstant{
			public static final String RADIO_FILE_UPLOAD_SELECT = "upLoadFile"; 
			public static final String RADIO_FILE_COMBO_SELECT = "selectFile";
		}
		
		public static class Elementi{
			public static final String POTASSIO = "Potassio"; 
			public static final String URANIO = "Uranio"; 
			public static final String TORIO = "Torio"; 
			public static final String CESIO = "Cesio";
			public static final String TORIO_URANIO = "eTh/eU";
			public static final String POTASSIO_URANIO = "k/eU";
			public static final String CHIQUADRO = "ChiSQ";
			public static final String ALLARMI = "Allarmi"; 
		}
		
		public static class TipoMsg{
			public static final String INFO = "INFO"; 
			public static final String WARM = "WARM"; 
			public static final String ERROR = "ERROR"; 
			public static final String FATAL_ERROR = "FATAL_ERROR"; 
		}
	}
}
