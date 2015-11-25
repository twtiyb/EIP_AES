package com.sf.module.bspcommon.util;

import com.sf.module.bspcommon.util.bspcodec.HmacSha512Coder;
import com.sf.module.bspcommon.util.bspcodec.HmacSha512CoderFactory;
import com.sf.module.bspcommon.util.bspcodec.impl.AES256CipherExternal;
import com.sf.module.bspcommon.util.bspcodec.impl.AES256CipherExternalFactory;
import com.sf.module.bspcommon.util.util.HttpUtil;

/**
 * Created by xuchun on 15/11/25.
 */
public class test {


    /**
     * 1.接口是使用webservice做的。所以使用Http方式post,需要加上webserivce的报文头，不能只请求那几个字段。最终要post的内容为目录下的 "测试报文" 文件。
     * 2.因为加密是使用aes 256位加密的。而默认jdk是不支持这么多位的。如果直接运行加密会报初始化失败。需要将压缩包内security.zip内的2个jar包放入项目项目的运行环境jre,或者jdk目录下lib下的security。将原来的替换掉。
     * 3.发送请求时Content-type 注意使用 text/xml
     */
    public static void main(String[] argv) {
        String sf_aes256cbc_key = "L9y8WBZwWzIT5tZhm1vsdSwQq87xN+Dv";
        String sf_sha512_secret_key = "4xWYXVT7nSBMqFJw6qITq3WD1rUEic33";
        String sf_partner_id = "WCKJTEST";
        String sf_partner_token = "WCKJTEST";
        String orderTest = "orderTest000012";
        String reportStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?> \n" +
                "<Request>\n" +
                "  <Header>\n" +
                "    <PartnerID>" + sf_partner_id + "</PartnerID>\n" +
                "    <PartnerToken>" + sf_partner_token + "</PartnerToken>\n" +
                "    <VersionID>V1.1</VersionID>\n" +
                "    <DocumentType/>\n" +
                "    <SenderID>" + sf_partner_id + "</SenderID>\n" +
                "    <ReceiverID>SFCP</ReceiverID>\n" +
                "    <Timestamp>2015-11-23 21:17:12</Timestamp>\n" +
                "    <RequestID>" + orderTest + "</RequestID>\n" +
                "    <Language>zh-CN</Language>\n" +
                "    <TimeZone>+08:00</TimeZone>\n" +
                "  </Header>\n" +
                "  <Body>\n" +
                "    <OrderList>\n" +
                "      <Order>\n" +
                "        <OrderNo>" + orderTest + "</OrderNo>\n" +
                "        <ExpressServiceCode>1</ExpressServiceCode>\n" +
                "        <IsGenMailNo>1</IsGenMailNo>\n" +
                "        <ParcelQuantity>1</ParcelQuantity>\n" +
                "        <TotalWeight>0.44</TotalWeight>\n" +
                "        <PaymentMethod>1</PaymentMethod>\n" +
                "        <PaymentType>1</PaymentType>\n" +
                "        <PaymentAccountNo>5713354213</PaymentAccountNo>\n" +
                "        <IsDoCall>0</IsDoCall>\n" +
                "        <NeedReturnTrackingNo>0</NeedReturnTrackingNo>\n" +
                "        <PickupMethod>2</PickupMethod>\n" +
                "        <DeliveryMethod>1</DeliveryMethod>\n" +
                "        <AdditionalDataList>\n" +
                "          <AdditionalData>\n" +
                "            <Key>cargoCount</Key>\n" +
                "            <Value>2</Value>\n" +
                "          </AdditionalData>\n" +
                "        </AdditionalDataList>\n" +
                "        <ShipFromAddress>\n" +
                "          <Contact>杭州优买科技有限公司</Contact>\n" +
                "          <Telephone>15036188965</Telephone>\n" +
                "          <Mobile>15036188965</Mobile>\n" +
                "          <CountryCode>CN</CountryCode>\n" +
                "          <StateOrProvince>浙江省</StateOrProvince>\n" +
                "          <City>杭州市</City>\n" +
                "          <County>江干区</County>\n" +
                "          <AddressLine1>浙江省杭州出口加工区内泰山路23号2-008,6号仓库</AddressLine1>\n" +
                "        </ShipFromAddress>\n" +
                "        <ShipToAddress>\n" +
                "          <Contact>涂璟</Contact>\n" +
                "          <Telephone>13706845074</Telephone>\n" +
                "          <Mobile>13706845074</Mobile>\n" +
                "          <CountryCode>CN</CountryCode>\n" +
                "          <StateOrProvince>浙江省</StateOrProvince>\n" +
                "          <City>宁波市</City>\n" +
                "          <County>江北区</County>\n" +
                "          <AddressLine1>江北区清河路28号,涂璟</AddressLine1>\n" +
                "        </ShipToAddress>\n" +
                "        <Parcels>\n" +
                "          <Length>0</Length>\n" +
                "          <Width>0</Width>\n" +
                "          <Height>0</Height>\n" +
                "          <Weight>0</Weight>\n" +
                "          <Volume>0</Volume>\n" +
                "          <TempRange>1</TempRange>\n" +
                "        </Parcels>\n" +
                "        <LineItem>\n" +
                "          <ItemName>Happy Baby 禧贝 3段有机混合谷物米粉 198克</ItemName>\n" +
                "          <ItemValue>0.0</ItemValue>\n" +
                "          <ItemValueCurrencyCode>CNY</ItemValueCurrencyCode>\n" +
                "          <ItemQuantity>2</ItemQuantity>\n" +
                "        </LineItem>\n" +
                "        <CustomsItem>\n" +
                "          <CustomsBatchs>2015-11-19</CustomsBatchs>\n" +
                "          <DeclaredValueCurrencyCode>CNY</DeclaredValueCurrencyCode>\n" +
                "          <DeclaredValue>68.00</DeclaredValue>\n" +
                "        </CustomsItem>\n" +
                "      </Order>\n" +
                "    </OrderList>\n" +
                "  </Body>\n" +
                "</Request>";
        AES256CipherExternal aes256 = AES256CipherExternalFactory.getAES256CipherExternalObject(sf_aes256cbc_key);
        HmacSha512Coder sha512 = HmacSha512CoderFactory.getAESCBCCoderObject(sf_sha512_secret_key);
        try {
            String MsgData = aes256.AES_Encode(reportStr);
            String DataDigest = sha512.generateHMAC(MsgData);
            String PartnerID = sf_partner_id;
            String ServiceCode = "OrderService";
            String result = "";

            String data = "<?xml version=\"1.0\" encoding=\"utf-8\"?> <S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                    "<S:Body><ns2:sfexpressService xmlns:ns2=\"http://service.expressservice.integration.sf.com/\"><MsgData>" + MsgData + "</MsgData><DataDigest>" + DataDigest + "</DataDigest>" +
                    "<PartnerID>" + PartnerID + "</PartnerID><ServiceCode>OrderService</ServiceCode></ns2:sfexpressService></S:Body></S:Envelope>";
            result = HttpUtil.sendPostUrl("http://218.17.248.244:7778/isp/ws/sfexpressService", data, "utf-8");
            System.out.println(data);
            System.out.println("-------------");
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}