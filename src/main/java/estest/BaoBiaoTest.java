package estest;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class BaoBiaoTest {
    public static void main(String[] args) throws UnknownHostException {
        Settings settings=Settings.builder().put("cluster.name","elasticsearch").build();
        //        TransportClient client=new PreBuiltTransportClient(settings);
        TransportClient client=new PreBuiltTransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"),9300));
        test(client);
    }

    public static void test(TransportClient client){





    }

}
