package com.radicalninja.restoid.data.event;

public class InnerDataEvent {

    public enum DataField {
        URL, REQUEST_TYPE, RESULT_TYPE, HEADERS, URL_PARAMS, BODY_TEXT
    }

    public static class Request {

        private DataField mField;

        public Request(DataField field) {
            mField = field;
        }

        public DataField getDataField() {
            return mField;
        }

    }

}
