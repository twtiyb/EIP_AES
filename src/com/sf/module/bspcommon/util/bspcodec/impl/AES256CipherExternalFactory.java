package com.sf.module.bspcommon.util.bspcodec.impl;

import java.util.HashMap;
import java.util.Map;


/**
 * 按加密密钥进行缓存AESCCBCCoder对象，提高频繁使用时的性能
 * 
 * @author 056235
 *
 */
public class AES256CipherExternalFactory {
	static Map<String, AES256CipherExternal> AES256CipherExternalMap = new HashMap<String, AES256CipherExternal>();

	public static AES256CipherExternal getAES256CipherExternalObject(String key) {
		if (null == AES256CipherExternalMap.get(key)) {
			AES256CipherExternal AES256CipherExternal = new AES256CipherExternal(key);

			AES256CipherExternalMap.put(key, AES256CipherExternal);
		}

		return AES256CipherExternalMap.get(key);
	}
	public static String AES256Encode(String data,String key) {
		AES256CipherExternal aes256 = AES256CipherExternalFactory.getAES256CipherExternalObject(key);
		String encodeText;
		try {
			encodeText = aes256.AES_Encode(data);
		} catch (Exception e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			return "error";
		}
		return encodeText;
		
	}
	public static String AES256Decode(String data,String key) {
		AES256CipherExternal aes256 = AES256CipherExternalFactory.getAES256CipherExternalObject(key);
		String decodeText;
		try {
			decodeText = aes256.AES_Decode(data);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			return "error:"+e.getMessage();
		}
		return decodeText;
		
	}
	public static void main(String[] args) throws Exception {
		AES256CipherExternal aes256 = AES256CipherExternalFactory.getAES256CipherExternalObject("VMFPZGQCekAfgBNohekfBO/QjLU+KXo9");
		String plainText;
		String encodeText;
		String decodeText;
		
		plainText  = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Request><Header><PartnerID>3711591543</PartnerID><PartnerToken>3711591543</PartnerToken><VersionID>V1.0</VersionID><DocumentType>order</DocumentType><SenderID>BLH3711591543</SenderID><ReceiverID>SFCP</ReceiverID><Timestamp>2015-07-14 15:41:46</Timestamp><RequestID>20150714154146198184</RequestID><Language>zh-CN</Language><TimeZone>+08:00</TimeZone></Header><Body><OrderList><Order><OrderNo>1138424823055805</OrderNo><ExpressServiceCode>1</ExpressServiceCode><IsGenMailNo>1</IsGenMailNo><ParcelQuantity>1</ParcelQuantity><MailNo></MailNo><TotalWeight>0.260</TotalWeight><TotalVolume></TotalVolume><PaymentMethod>1</PaymentMethod><PaymentType>1</PaymentType><PaymentAccountNo>3711591543</PaymentAccountNo><IsDoCall>0</IsDoCall><NeedReturnTrackingNo>1</NeedReturnTrackingNo><ReturnTrackingNo></ReturnTrackingNo><PickupMethod>1</PickupMethod><DeliveryMethod>1</DeliveryMethod><RequestPickupTimeStart></RequestPickupTimeStart><RequestPickupTimeEnd></RequestPickupTimeEnd><Remark></Remark><AdditionalDataList/><ShipFromAddress><CompanyName></CompanyName><Contact>自然之宝海外旗舰店</Contact><SFStoreCode></SFStoreCode><Telephone>400-990-1062</Telephone><Mobile></Mobile><Email></Email><CountryCode>CN</CountryCode><StateOrProvince>河南省</StateOrProvince><City>郑州市</City><County>经开区</County><AddressLine1>郑州市经济开发区经北二路第九大街东500</AddressLine1><AddressLine2></AddressLine2><ShipperCode></ShipperCode><PostalCode></PostalCode><AdditionalDataList/></ShipFromAddress><ShipToAddress><CompanyName></CompanyName><Contact>潘迎德</Contact><SFStoreCode></SFStoreCode><Telephone></Telephone><Mobile>13688169387</Mobile><Email></Email><CountryCode>CN</CountryCode><StateOrProvince>四川省</StateOrProvince><City>成都市</City><County>新都区</County><AddressLine1>新都镇新都大道西南石油大学教工38栋104室</AddressLine1><AddressLine2></AddressLine2><DeliveryCode></DeliveryCode><PostalCode></PostalCode><AdditionalDataList/></ShipToAddress><Parcels><PackageType></PackageType><Length></Length><Width></Width><Height></Height><Weight></Weight><Volume></Volume><TempRange></TempRange></Parcels></Order></OrderList></Body></Request>";
		encodeText = aes256.AES_Encode(plainText);	
		System.out.println("AES256_Encode : "+encodeText);
		 
		// Decrypt
		String str="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";
		decodeText = aes256.AES_Decode(str);
		System.out.println("AES256_Decode : "+decodeText);
		
	}
}
