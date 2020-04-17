/*
CREATE TABLE SttlmIntlr_FinInstrm_Mapping ( 
   SttlmIntlr_Mapping_id IDENTITY NOT NULL PRIMARY KEY,
   SI_FI_Type VARCHAR(50) NOT NULL,
   Titel_Arten VARCHAR(255)
);
*/
INSERT INTO SttlmIntlr_FinInstrm_Mapping(SI_FI_Type,Titel_Arten) VALUES ('Eqty', '201');
INSERT INTO SttlmIntlr_FinInstrm_Mapping(SI_FI_Type,Titel_Arten) VALUES ('SvrgnDebt', null);
INSERT INTO SttlmIntlr_FinInstrm_Mapping(SI_FI_Type,Titel_Arten) VALUES ('Bd', '122,151');
INSERT INTO SttlmIntlr_FinInstrm_Mapping(SI_FI_Type,Titel_Arten) VALUES ('OthrTrfblScties', '250');
INSERT INTO SttlmIntlr_FinInstrm_Mapping(SI_FI_Type,Titel_Arten) VALUES ('XchgTradgFnds', null);
INSERT INTO SttlmIntlr_FinInstrm_Mapping(SI_FI_Type,Titel_Arten) VALUES ('CllctvInvstmtUdrtkgs', null);
INSERT INTO SttlmIntlr_FinInstrm_Mapping(SI_FI_Type,Titel_Arten) VALUES ('MnyMktInstrm', null);
INSERT INTO SttlmIntlr_FinInstrm_Mapping(SI_FI_Type,Titel_Arten) VALUES ('EmssnAllwnc', null);
INSERT INTO SttlmIntlr_FinInstrm_Mapping(SI_FI_Type,Titel_Arten) VALUES ('OthrFinInstrms', null);

/*
CREATE TABLE SttlmIntlr_TxTp_Mapping ( 
   SttlmIntlr_Mapping_id IDENTITY NOT NULL PRIMARY KEY,
   SI_TT_Type VARCHAR(50) NOT NULL,
   TRC VARCHAR(255)
);
*/

INSERT INTO SttlmIntlr_TxTp_Mapping(SI_TT_Type,TRC) VALUES ('SctiesBuyOrSell', 'ACT,VCT');
INSERT INTO SttlmIntlr_TxTp_Mapping(SI_TT_Type,TRC) VALUES ('CollMgmtOpr', null);
INSERT INTO SttlmIntlr_TxTp_Mapping(SI_TT_Type,TRC) VALUES ('SctiesLndgOrBrrwg', null);
INSERT INTO SttlmIntlr_TxTp_Mapping(SI_TT_Type,TRC) VALUES ('RpAgrmt', null);
INSERT INTO SttlmIntlr_TxTp_Mapping(SI_TT_Type,TRC) VALUES ('OthrTxs', 'SOU,VCN');

