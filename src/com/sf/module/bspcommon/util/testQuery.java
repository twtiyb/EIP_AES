package com.sf.module.bspcommon.util;

import com.sf.module.bspcommon.util.bspcodec.HmacSha512Coder;
import com.sf.module.bspcommon.util.bspcodec.HmacSha512CoderFactory;
import com.sf.module.bspcommon.util.bspcodec.impl.AES256CipherExternal;
import com.sf.module.bspcommon.util.bspcodec.impl.AES256CipherExternalFactory;
import com.sf.module.bspcommon.util.util.HttpUtil;

/**
 * Created by xuchun on 15/11/26.
 */
public class testQuery {
    public static void main(String[] args) {

        String orderNo = "2015120110421031003030501";
        String reportStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?> \n" +
                "<Request>\n" +
                "  <Header>\n" +
                "    <PartnerID>" + Constant.sf_partner_id + "</PartnerID>\n" +
                "    <PartnerToken>" + Constant.sf_partner_token + "</PartnerToken>\n" +
                "    <VersionID>V1.1</VersionID>\n" +
                "    <DocumentType/>\n" +
                "    <SenderID>" + Constant.sf_partner_id + "</SenderID>\n" +
                "    <ReceiverID>SFCP</ReceiverID>\n" +
                "    <Timestamp>2015-11-23 21:17:12</Timestamp>\n" +
                "    <RequestID>"+ (int)(Math.random()*10000)+"</RequestID>\n" +
                "    <Language>zh-CN</Language>\n" +
                "    <TimeZone>+08:00</TimeZone>\n" +
                "  </Header>\n" +
                "<Body>\n" +
                "   <OrderSearch>\n" +
                "       <OrderNo>"+orderNo+"</OrderNo>\n" +
                "   </OrderSearch>\n" +
                "</Body>\n"+
                "</Request>"

                ;

        AES256CipherExternal aes256 = AES256CipherExternalFactory.getAES256CipherExternalObject(Constant.sf_aes256cbc_key);
        HmacSha512Coder sha512 = HmacSha512CoderFactory.getAESCBCCoderObject(Constant.sf_sha512_secret_key);
        try {
            String MsgData = aes256.AES_Encode(reportStr);
            String DataDigest = sha512.generateHMAC(MsgData);
            String PartnerID = Constant.sf_partner_id;
            String ServiceCode = "OrderResultQuery";
            String result = "";
            System.out.println(reportStr);


            String data = "<?xml version=\"1.0\" encoding=\"utf-8\"?> <S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                    "<S:Body><ns2:sfexpressService xmlns:ns2=\"http://service.expressservice.integration.sf.com/\"><MsgData>" + MsgData + "</MsgData><DataDigest>" + DataDigest + "</DataDigest>" +
                    "<PartnerID>" + PartnerID + "</PartnerID><ServiceCode>"+ServiceCode+"</ServiceCode></ns2:sfexpressService></S:Body></S:Envelope>";
            System.out.println(data);

            result = HttpUtil.sendPostUrl("http://218.17.248.244:7778/isp/ws/sfexpressService", data, "utf-8");

            System.out.println(result);
        }catch (Exception e) {

        }
    }
}
