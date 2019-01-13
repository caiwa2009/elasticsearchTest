package estest;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.joda.time.DateTime;

import java.io.IOException;
import java.net.InetAddress;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

public class BaseDemo {
    public static void main(String[] args) throws IOException {
        //如果设置了集群则需要添加集群的名称
        Settings settings=Settings.builder().put("cluster.name","elasticsearch").build();
        //        TransportClient client=new PreBuiltTransportClient(settings);
        TransportClient client=new PreBuiltTransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"),9300));
//        createIndexByKey(client);
//        queryDocument(client);
//        updateDocument(client);
//        deleteDocument(client);
//        client.close();
//        multiGetTest
//        test(client);
    }

    private static void test(TransportClient client){



    }

    private static void multiGetTest(TransportClient client) throws IOException {
        XContentBuilder jsonbuild=jsonBuilder().startObject()
                .field("user","zhangshan")
                .field("birthday",new DateTime())
                .field("age",22)
                .endObject();

        /**
         * 如果文档不存在，upsert元素的内容将用于添加新文档，如果存在则更新
         */
        IndexRequest indexRequest=new IndexRequest();
        UpdateRequest updateRequest=new UpdateRequest("index","type","1")
                .doc(jsonBuilder()
                        .startObject()
                        .field("name","zhangsan")
                        .endObject())
                .upsert(indexRequest);

        /**
         * MultiGet响应，可以索引和类型不一致
         */
        MultiGetResponse multiGetItemResponses=client.prepareMultiGet()
                .add("twitter", "tweet", "1")
                .add("twitter", "tweet", "2", "3", "4")
                .add("another", "type", "foo")
                .get();
        for (MultiGetItemResponse itemResponse : multiGetItemResponses) {
            GetResponse response = itemResponse.getResponse();
            if (response.isExists()) {
                String json = response.getSourceAsString();
            }
        }
    }

//删除文档
    private static void deleteDocument(TransportClient client) {
        DeleteResponse response=client.prepareDelete("index","type","1").get();

    }

    //    更新文档
    private static void updateDocument(TransportClient client) throws IOException {
        client.prepareUpdate("index","type","1")
                .setDoc(jsonBuilder()
                        .startObject()
                        .field("gender","male")
                        .endObject()
                ).get();
    }


    //    查询文档
    private static void queryDocument(TransportClient client) {
        GetResponse response=client.prepareGet("index","type","1").get();
        System.out.println(response);
    }

    //    创建索引，文档有java和json两种写法
    private static void createIndexByKey(TransportClient client) throws IOException {
//        IndexResponse response=client.prepareIndex("index","type","1")
////                        .setSource(XContentFactory.jsonBuilder()
//                .setSource(jsonBuilder()
//                        .startObject()
//                        .field("user","kimchy")
//                        .field("postDate",new Date())
//                        .field("message","try out Elasticsearch")
//                        .endObject()
//                ).get();


//        上述创建索引也可以写成JSON格式
        String json = "{" +
                "\"user\":\"kimchy\"," +
                "\"postDate\":\"2013-01-30\"," +
                "\"message\":\"trying out Elasticsearch\"" +
                "}";
        IndexResponse response=client.prepareIndex("index","type")
                .setSource(json, XContentType.JSON).get();

    }
}
