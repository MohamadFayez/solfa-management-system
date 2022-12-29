INSERT INTO GEN_PROCESS_LDAPGROUPS  (PROCESS_ID, GROUP_NAME) VALUES ('99', 'SOLFA_ADMIN_GROUP');
INSERT INTO GEN_PROCESSES  (PROCESS_ID, PROCESS_NAME, REQUIRES_ADMINISTRATION) VALUES ('99', 'Solfa', '1');
INSERT INTO GEN_PROCESS_NEWAPP_LINKS  (PROCESS_ID, WEBAPP_URL, CATEGORY, URL_TYPE, HTTP_ADMIN_URL) VALUES ('99', 'Solfa', 'FINANCE', 'NEW', 'http://127.0.0.1:7101/solfa/faces/pages/solfa.jsf');
INSERT INTO GEN_PROCESS_ADMIN_LDAPGROUPS  (PROCESS_ID, ADMIN_GROUP_NAME) VALUES ('99', 'SOLFA_ADMIN_GROUP');
INSERT INTO RV_PROCESSES  (RVPRO_ID, RVPRO_DISPLAY_NAME, RVPRO_ICON_NAME, RVPRO_SORTING, RVSUB_CODE, RVPRO_BIG_NAME) VALUES ('99', 'Solfa', 'VFE_BPM_RV_LAP.jpg', '10', '1', 'VFE_BPM_RV_LAP.jpg');
