//package com.example.Personal_Trainer;
//
//public class InbodyCategory_test {
//    private TextView txtView;
//    private EditText editText1, editText2;
//    Button insertBtn;
//
//    private static String IP = ""; //서버 없이 사용하는 IP가 있다면 저장해서 사용하면 된다.
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        // 사용할 액티비티 선언
//        editText1 = findViewById(R.id.edtText1);
//        editText2 = findViewById(R.id.edtText2);
//        insertBtn = findViewById(R.id.insertBtn);
//
//        // String url = "http://" + IP + "/php파일명.php";
//        String url = "http://" + IP + "/InsertData.php";
////        selectDatabase selectDatabase = new selectDatabase(url, null);
////        selectDatabase.execute(); // AsyncTask는 .excute()로 실행된다.
//
//    }
//
//
//    class selectDatabase extends AsyncTask<Void, Void, String> {
//
//        private String url1;
//        private ContentValues values1;
//        String result1; // 요청 결과를 저장할 변수.
//
//        public selectDatabase(String url, ContentValues contentValues) {
//            this.url1 = url;
//            this.values1 = contentValues;
//        }
//
//        @Override
//        protected String doInBackground(Void... params) {
//            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
//            result1 = requestHttpURLConnection.request(url1, values1); // 해당 URL로 부터 결과물을 얻어온다.
//            return result1; // 여기서 당장 실행 X, onPostExcute에서 실행
//        }
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//            //txtView.setText(s); // 파서 없이 전체 출력
//            doJSONParser(s); // 파서로 전체 출력
//        }
//    }
//
//    // 받아온 json 데이터를 파싱합니다..
//    public void doJSONParser(String string) {
//        try {
//            String result = "";
//            JSONObject jsonObject = new JSONObject(string);
//            JSONArray jsonArray = jsonObject.getJSONArray("ttest");
//
//            for (int i=0; i < jsonArray.length(); i++) {
//                JSONObject output = jsonArray.getJSONObject(i);
//                result += output.getString("ed1txt")
//                        + " / "
//                        + output.getString("ed2txt")
//                        + "\n";
//            }
//
//            txtView = findViewById(R.id.txtView);
//            txtView.setText(result);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//
//}
