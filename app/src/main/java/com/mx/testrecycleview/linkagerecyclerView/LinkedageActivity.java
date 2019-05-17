package com.mx.testrecycleview.linkagerecyclerView;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.gson.Gson;
import com.kunminx.linkage.LinkageRecyclerView;
import com.kunminx.linkage.bean.DefaultGroupedItem;
import com.mx.testrecycleview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 更加详细使用见 https://github.com/KunMinX/Linkage-RecyclerView
 */
public class LinkedageActivity extends AppCompatActivity {

    List<DefaultGroupedItem> items = new ArrayList<>();
    LinkageRecyclerView linkageRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linkedage);

        getData();
        linkageRecyclerView = findViewById(R.id.linkage);
        linkageRecyclerView.init(items);

    }

    private void getData() {

        String json1 = "{\n" +
                "    \"header\": \"优惠\",\n" +
                "    \"isHeader\": true\n" +
                "  }";
        String json2 = "{\n" +
                "    \"isHeader\": false,\n" +
                "    \"info\": {\n" +
                "      \"content\": \"好吃的食物，增肥神器，有求必应\",\n" +
                "      \"group\": \"优惠\",\n" +
                "      \"title\": \"全家桶\"\n" +
                "    }\n" +
                "  }";
        String json5 = "{\n" +
                "    \"isHeader\": false,\n" +
                "    \"info\": {\n" +
                "      \"content\": \"爆款热卖，月销超过 999 件\",\n" +
                "      \"group\": \"热卖\",\n" +
                "      \"title\": \"烤全翅\"\n" +
                "    }\n" +
                "  }";


        String json3 = "{\n" +
                "    \"header\": \"热卖\",\n" +
                "    \"isHeader\": true\n" +
                "  }";
        String json4 = "{\n" +
                "    \"isHeader\": false,\n" +
                "    \"info\": {\n" +
                "      \"content\": \"爆款热卖，月销超过 999 件\",\n" +
                "      \"group\": \"热卖\",\n" +
                "      \"title\": \"烤全翅\"\n" +
                "    }\n" +
                "  }";

        Gson gson = new Gson();

        DefaultGroupedItem item1 =  gson.fromJson(json1,DefaultGroupedItem.class);
        DefaultGroupedItem item2 =  gson.fromJson(json2,DefaultGroupedItem.class);
        DefaultGroupedItem item3 =  gson.fromJson(json3,DefaultGroupedItem.class);
        DefaultGroupedItem item4 =  gson.fromJson(json4,DefaultGroupedItem.class);
        DefaultGroupedItem item5 =  gson.fromJson(json5,DefaultGroupedItem.class);

        items.add(item1);
        items.add(item2);
        items.add(item2);
        items.add(item2);
        items.add(item2);
        items.add(item3);
        items.add(item4);
        items.add(item5);
        items.add(item5);
        items.add(item5);
        items.add(item5);
        items.add(item5);
        items.add(item5);
        items.add(item5);
        items.add(item5);
    }

}
