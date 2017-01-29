package com.zillion.vault;

import com.bettercloud.vault.Vault;
import com.bettercloud.vault.VaultConfig;
import com.bettercloud.vault.VaultException;
import com.bettercloud.vault.response.LogicalResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lpresswood on 1/29/17.
 * Copyright Zillion.com
 */
public class VaultTest {
    private static final String VAULT_URL="http://127.0.0.1:8200";
    // This will change for your setup and your devops team may put this in an environment variable
    private static final String YOUR_ROOT_TOKEN="851089d9-8ceb-4558-ef88-7e3e17a2eefc";

    public static void main(String ... args){

        try {
            VaultConfig vaultConfig = new VaultConfig(VAULT_URL, YOUR_ROOT_TOKEN);
            Vault vault = new Vault(vaultConfig);

            final String application="ra-memfe";
            final String secretKey = "secret/" + application;

            final Map<String, String> secrets = new HashMap<>();
            secrets.put("adminUser","raAdmin");
            secrets.put("adminPwd","testPwd");

            vault.logical().write(secretKey,secrets);


            final String adminUser = vault.logical()
                    .read(secretKey)
                    .getData().get("adminUser");

            final String adminPwd = vault.logical()
                    .read(secretKey)
                    .getData().get("adminPwd");


            System.out.println("Admin User Id - " + adminUser);
            System.out.println("Admin Pwd - " + adminPwd);

        }catch(VaultException ve){
            System.err.print(ve.getMessage());
        }

    }


}
