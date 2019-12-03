package utils;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import dao.Dao;

public class Utilities {
	/**
	 * Uses the “PBKDF2” algorithm  When called, this method will hash all the passwords present on the database
	 * @throws SQLException 
	 * @throws InvalidKeySpecException 
	 * @throws NoSuchAlgorithmException 
	 */
	public static void HashDbPasswords() throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException
	{
		String query = "Select password, user_id from User"; 
		ResultSet rs = Dao.getConnection().prepareStatement(query).executeQuery();
		while(rs.next())
		{
			String originalPassword = rs.getString("password");
			String generatedSecuredPasswordHash = generateStorngPasswordHash(originalPassword);
			String querySetPassword = String.format("UPDATE User SET password = ? WHERE user_id = ?");
			
			// Update the old password to an hashed one with 
			PreparedStatement stment = Dao.getConnection().prepareStatement(querySetPassword);
			stment.setString(1, generatedSecuredPasswordHash);
			stment.setInt(2, rs.getInt("user_id"));
			stment.executeUpdate();
		}
	}

    private static byte[] getSalt() throws NoSuchAlgorithmException
    {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }
    
	public static String generateStorngPasswordHash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
		
			// The greater the iterations, the greater the protection is against bruteforcing
		 	int iterations = 800;
	        char[] chars = password.toCharArray();
	        byte[] salt = getSalt();
	         
	        int keyLength = 64 * 8;
	        PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, keyLength);
	        SecretKeyFactory skf = SecretKeyFactory.getInstance(Constants.ADV_HASH_ALG_NAME);
	        byte[] hash = skf.generateSecret(spec).getEncoded();
	        return iterations + ":" + toHex(salt) + ":" + toHex(hash);
	}
	
	private static String toHex(byte[] array) throws NoSuchAlgorithmException
    {
		// We generate an integer of undefined size
        BigInteger bi = new BigInteger(1, array);
        
        // We set the radix to 16 because we want an hex number
        String hex = bi.toString(16);
        
        
        int paddingLength = (array.length * 2) - hex.length();
        if(paddingLength > 0)
        {
        	// %0 means the first argument in the string.format method
            return String.format("%0"  +paddingLength + "d", 0) + hex;
        }else{
            return hex;
        }
    }
	
    public static boolean validatePassword(String originalPassword, String storedPassword) throws NoSuchAlgorithmException, InvalidKeySpecException
    {
    	// Format of the stored password : {iteration count}:{salt}:{hash}, we get each one of them
        String[] parts = storedPassword.split(":");
        int iterations = Integer.parseInt(parts[0]);
        byte[] salt = hexToBytes(parts[1]);
        byte[] hash = hexToBytes(parts[2]);
         
        PBEKeySpec spec = new PBEKeySpec(originalPassword.toCharArray(), salt, iterations, hash.length * 8);
        SecretKeyFactory secretKeyFact = SecretKeyFactory.getInstance(Constants.ADV_HASH_ALG_NAME);
        byte[] testHash = secretKeyFact.generateSecret(spec).getEncoded();
         
        int diff = hash.length ^ testHash.length;
        for(int i = 0; i < hash.length && i < testHash.length; i++)
        {
            diff |= hash[i] ^ testHash[i];
        }
        return diff == 0;
    }
    private static byte[] hexToBytes(String hex) throws NoSuchAlgorithmException
    {
        byte[] bytes = new byte[hex.length() / 2];
        for(int i = 0; i<bytes.length ;i++)
        {
            bytes[i] = (byte)Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }
	
}
