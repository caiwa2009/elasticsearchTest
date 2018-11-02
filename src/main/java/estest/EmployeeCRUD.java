package estest;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;

public class EmployeeCRUD {
    public static void main(String[] args) throws Exception {
        Settings settings=Settings.builder().put("cluster.name","elasticsearch").build();
//        TransportClient client=new PreBuiltTransportClient(settings);
        TransportClient client=new PreBuiltTransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"),9300));
        employeeQuery(client);
        client.close();
    }

    /**
     * 注意matchQuery和matchPhraseQuery的区别是matchQuery会被分词，而matchPhraseQuery不会被分词
     * @param client
     */
//    查询员工列表中position中含有technique年龄在30到40之间，分页查询
    private static void employeeQuery(TransportClient client) {
        SearchResponse response=client.prepareSearch("company")
                .setTypes("employee").setVersion(true)
                .setQuery(QueryBuilders.matchPhraseQuery("position","technique"))
                .setPostFilter(QueryBuilders.rangeQuery("age").from(30).to(40))
                .setFrom(0).setSize(1).get();
//        response.get
        SearchHit[] hits = response.getHits().getHits();
        for (int i = 0; i <hits.length ; i++) {
            System.out.println(hits[i].getSourceAsString());
        }
//        PropertiesConfiguration

    }
}
