package com.vasa.dimensionslab;

public class Constants {
	public interface ACTION {
		public static int ACTION_GET_FILE = 1;
 	}
 
 	public interface NOTIFICATION_ID {
 		public static int FOREGROUND_SERVICE = 101;
 	}

 	public interface PERMISSION {
	 	public static final int ACCESS_NETWORK_STATE = 2;
		public static final int ACCESS_FINE_LOCATION = 3;
		public static final int WAKE_LOCK = 4;
		public static final int INTERNET = 5;
		public static final int READ_PHONE_STATE = 6;
		public static final int WRITE_EXTERNAL_STORAGE = 7;
		public static final int READ_EXTERNAL_STORAGE = 11;
		public static final int CAMERA = 8;
		public static final int RECORD_AUDIO = 9;
		public static final int USE_FINGERPRINT = 10;
	}

	public interface NOTIFICATION {
		public static final String CHANNEL_ID = "my_channel_01";
	}

	public interface LOCATION {
		public static final int MIN_DISTANCE = 1000;
		public static final int UPDATE_INTERVAL_IN_MILLISECONDS = 10000;
	}

	public interface SQLLITE {
		public static final int DATABASE_VERSION = 1;
    	public static final String DATABASE_NAME = "FeedReader.db";

    	public static final String TABLE_NAME = "geo";
        public static final String COLUMN_NAME_PLAYERID = "playerid";
        public static final String COLUMN_NAME_LAT = "lat";
        public static final String COLUMN_NAME_LONG = "long";
        public static final String COLUMN_NAME_ALT = "alt";
        public static final String COLUMN_NAME_SPEED = "speed";
        public static final String COLUMN_NAME_TIMESTAMP = "timestamp";

        public static final String SQL_CREATE_GEO =
        	"CREATE TABLE geo ( _id integer primary key autoincrement,playerid TEXT,lat TEXT, long TEXT, alt TEXT, speed TEXT, timestamp TEXT)";

		public static final String SQL_DELETE_GEO =
    		"DROP TABLE IF EXISTS geo";
	}

	public interface TAGS {
 		public static final String TAG = "manar";
 		public static final String SED_BROADCAST = "SED_BROADCAST";
 		public static final String OBJECT_TAG = "OBJECT_TAG";
 		public static final String RENDER_TAG = "RENDER_TAG";
 		public static final String UTILS_TAG = "UTILS_TAG";
 		public static final String STLVIEW_TAG = "STLVIEW_TAG";
 		public static final String STLVIEWACT_TAG = "STLVIEWACT_TAG";
		public static final String STLPARSER_TAG = "STLPARSER_TAG";
 	}
}