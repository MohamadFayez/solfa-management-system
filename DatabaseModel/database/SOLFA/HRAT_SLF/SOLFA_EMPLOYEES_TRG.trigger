-- <?xml version = '1.0' encoding = 'UTF-8'?>
-- <trigger xmlns="http://xmlns.oracle.com/jdeveloper/1211/offlinedb">
--   <name>SOLFA_EMPLOYEES_TRG</name>
--   <enabled>true</enabled>
--   <properties>
--     <entry>
--       <key>OfflineDBConstants.IMPORT_SOURCE_CONNECTION</key>
--       <value class="java.lang.String">solfaConn</value>
--     </entry>
--     <entry>
--       <key>OfflineDBConstants.IMPORT_SOURCE_ID</key>
--       <value class="oracle.javatools.db.ReferenceID">
--         <name>SOLFA_EMPLOYEES_TRG</name>
--         <identifier class="java.math.BigDecimal">20586</identifier>
--         <schemaName>HRAT_SLF</schemaName>
--         <type>TRIGGER</type>
--       </value>
--     </entry>
--   </properties>
--   <statementLevel>true</statementLevel>
-- </trigger>

CREATE OR REPLACE
trigger SOLFA_EMPLOYEES_TRG  
   before insert on "HRAT_SLF"."SOLFA_EMPLOYEES" 
   for each row 
begin  
   if inserting then 
      if :NEW."SOLFA_EMP_ID" is null then 
         select SOLFA_EMPLOYEES_SEQ.nextval into :NEW."SOLFA_EMP_ID" from dual; 
      end if; 
   end if; 
end;
/
