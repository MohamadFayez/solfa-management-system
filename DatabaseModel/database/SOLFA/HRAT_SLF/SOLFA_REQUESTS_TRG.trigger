-- <?xml version = '1.0' encoding = 'UTF-8'?>
-- <trigger xmlns="http://xmlns.oracle.com/jdeveloper/1211/offlinedb">
--   <name>SOLFA_REQUESTS_TRG</name>
--   <enabled>true</enabled>
--   <properties>
--     <entry>
--       <key>OfflineDBConstants.IMPORT_SOURCE_CONNECTION</key>
--       <value class="java.lang.String">solfaConn</value>
--     </entry>
--     <entry>
--       <key>OfflineDBConstants.IMPORT_SOURCE_ID</key>
--       <value class="oracle.javatools.db.ReferenceID">
--         <name>SOLFA_REQUESTS_TRG</name>
--         <identifier class="java.math.BigDecimal">20588</identifier>
--         <schemaName>HRAT_SLF</schemaName>
--         <type>TRIGGER</type>
--       </value>
--     </entry>
--   </properties>
--   <statementLevel>true</statementLevel>
-- </trigger>

CREATE OR REPLACE
trigger SOLFA_REQUESTS_TRG  
   before insert on "HRAT_SLF"."SOLFA_REQUESTS" 
   for each row 
begin  
   if inserting then 
      if :NEW."REQUEST_NUMBER" is null then 
         select SOLFA_REQUESTS_SEQ.nextval into :NEW."REQUEST_NUMBER" from dual; 
      end if; 
   end if; 
end;
/
