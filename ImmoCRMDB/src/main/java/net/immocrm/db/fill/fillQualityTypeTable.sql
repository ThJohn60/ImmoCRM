INSERT INTO quality_type (id, name, category, data_type, property_types, active, internal, created_on)
	VALUES (NEXT VALUE FOR quality_type_seq, 'Wohnfläche', 'gen', 'area', 'WHB', 1, 1, CURRENT_TIMESTAMP);
INSERT INTO quality_type (id, name, category, data_type, property_types, active, internal, created_on)
	VALUES (NEXT VALUE FOR quality_type_seq, 'Nutzfläche', 'gen', 'area', 'WHBS', 1, 1, CURRENT_TIMESTAMP);
INSERT INTO quality_type (id, name, category, data_type, property_types, active, internal, created_on)
	VALUES (NEXT VALUE FOR quality_type_seq, 'Baujahr', 'gen', 'year', 'WHBS', 1, 1, CURRENT_TIMESTAMP);
INSERT INTO quality_type (id, name, category, data_type, property_types, active, internal, created_on)
	VALUES (NEXT VALUE FOR quality_type_seq, 'Grundstück', 'gen', 'area', 'WHBG', 1, 1, CURRENT_TIMESTAMP);
INSERT INTO quality_type (id, name, category, data_type, property_types, active, internal, created_on)
	VALUES (NEXT VALUE FOR quality_type_seq, 'Anz. Zimmer', 'gen', 'num', 'WHB', 1, 1, CURRENT_TIMESTAMP);

INSERT INTO quality_type (id, name, category, data_type, property_types, active, internal, created_on)
	VALUES (NEXT VALUE FOR quality_type_seq, 'Etage', 'gen', 'num', 'W', 1, 1, CURRENT_TIMESTAMP);
	
--INSERT INTO quality_type (id, name, category, data_type, property_types, active, internal, created_on)
--	VALUES (NEXT VALUE FOR quality_type_seq, 'Kaufdatum', 'gen', 'num', 'WHB', 1, 1, CURRENT_TIMESTAMP);

	
INSERT INTO quality_type (id, name, category, data_type, property_types, active, internal, created_on)
	VALUES (NEXT VALUE FOR quality_type_seq, 'Kaufpreis', 'intern', 'curr', 'WHB', 1, 1, CURRENT_TIMESTAMP);
INSERT INTO quality_type (id, name, category, data_type, property_types, active, internal, created_on)
	VALUES (NEXT VALUE FOR quality_type_seq, 'Mietpreis', 'gen', 'curr', 'WHB', 1, 1, CURRENT_TIMESTAMP);
INSERT INTO quality_type (id, name, category, data_type, property_types, active, internal, created_on)
	VALUES (NEXT VALUE FOR quality_type_seq, 'Hausgeld', 'gen', 'curr', 'WHB', 1, 1, CURRENT_TIMESTAMP);
INSERT INTO quality_type (id, name, category, data_type, property_types, active, internal, created_on)
	VALUES (NEXT VALUE FOR quality_type_seq, 'Letztes Renovierungjahr', 'gen', 'year', 'WHB', 1, 1, CURRENT_TIMESTAMP);

	
INSERT INTO quality_type (id, name, category, data_type, property_types, active, internal, created_on)
	VALUES (NEXT VALUE FOR quality_type_seq, 'Eigentumswohnung', 'type', 'bool', 'W', 1, 1, CURRENT_TIMESTAMP);
INSERT INTO quality_type (id, name, category, data_type, property_types, active, internal, created_on)
	VALUES (NEXT VALUE FOR quality_type_seq, 'Erdgeschosswohnung', 'type', 'bool', 'W', 1, 1, CURRENT_TIMESTAMP);
INSERT INTO quality_type (id, name, category, data_type, property_types, active, internal, created_on)
	VALUES (NEXT VALUE FOR quality_type_seq, 'Dachgeschosswohnung', 'type', 'bool', 'W', 1, 1, CURRENT_TIMESTAMP);
INSERT INTO quality_type (id, name, category, data_type, property_types, active, internal, created_on)
	VALUES (NEXT VALUE FOR quality_type_seq, 'Maisonette', 'type', 'bool', 'W', 1, 1, CURRENT_TIMESTAMP);
INSERT INTO quality_type (id, name, category, data_type, property_types, active, internal, created_on)
	VALUES (NEXT VALUE FOR quality_type_seq, 'Penthouse', 'type', 'bool', 'W', 1, 1, CURRENT_TIMESTAMP);
INSERT INTO quality_type (id, name, category, data_type, property_types, active, internal, created_on)
	VALUES (NEXT VALUE FOR quality_type_seq, 'Loft', 'type', 'bool', 'W', 1, 1, CURRENT_TIMESTAMP);
INSERT INTO quality_type (id, name, category, data_type, property_types, active, internal, created_on)
	VALUES (NEXT VALUE FOR quality_type_seq, 'Einfamilienhaus', 'type', 'bool', 'H', 1, 1, CURRENT_TIMESTAMP);
INSERT INTO quality_type (id, name, category, data_type, property_types, active, internal, created_on)
	VALUES (NEXT VALUE FOR quality_type_seq, 'Zweifamilienhaus', 'type', 'bool', 'H', 1, 1, CURRENT_TIMESTAMP);
INSERT INTO quality_type (id, name, category, data_type, property_types, active, internal, created_on)
	VALUES (NEXT VALUE FOR quality_type_seq, 'Mehrfamilienhaus', 'type', 'bool', 'H', 1, 1, CURRENT_TIMESTAMP);
INSERT INTO quality_type (id, name, category, data_type, property_types, active, internal, created_on)
	VALUES (NEXT VALUE FOR quality_type_seq, 'Bungalow', 'type', 'bool', 'H', 1, 1, CURRENT_TIMESTAMP);
INSERT INTO quality_type (id, name, category, data_type, property_types, active, internal, created_on)
	VALUES (NEXT VALUE FOR quality_type_seq, 'Reihenmittelhaus', 'type', 'bool', 'H', 1, 1, CURRENT_TIMESTAMP);
INSERT INTO quality_type (id, name, category, data_type, property_types, active, internal, created_on)
	VALUES (NEXT VALUE FOR quality_type_seq, 'Reiheneckhaus', 'type', 'bool', 'H', 1, 1, CURRENT_TIMESTAMP);
INSERT INTO quality_type (id, name, category, data_type, property_types, active, internal, created_on)
	VALUES (NEXT VALUE FOR quality_type_seq, 'Doppelhaushälfte', 'type', 'bool', 'H', 1, 1, CURRENT_TIMESTAMP);
INSERT INTO quality_type (id, name, category, data_type, property_types, active, internal, created_on)
	VALUES (NEXT VALUE FOR quality_type_seq, 'Büro', 'type', 'bool', 'B', 1, 1, CURRENT_TIMESTAMP);
INSERT INTO quality_type (id, name, category, data_type, property_types, active, internal, created_on)
	VALUES (NEXT VALUE FOR quality_type_seq, 'Ladengeschäft', 'type', 'bool', 'B', 1, 1, CURRENT_TIMESTAMP);
INSERT INTO quality_type (id, name, category, data_type, property_types, active, internal, created_on)
	VALUES (NEXT VALUE FOR quality_type_seq, 'Halle', 'type', 'bool', 'B', 1, 1, CURRENT_TIMESTAMP);
INSERT INTO quality_type (id, name, category, data_type, property_types, active, internal, created_on)
	VALUES (NEXT VALUE FOR quality_type_seq, 'Bürogebäude', 'type', 'bool', 'B', 1, 1, CURRENT_TIMESTAMP);
INSERT INTO quality_type (id, name, category, data_type, property_types, active, internal, created_on)
	VALUES (NEXT VALUE FOR quality_type_seq, 'Gaststätte', 'type', 'bool', 'B', 1, 1, CURRENT_TIMESTAMP);
INSERT INTO quality_type (id, name, category, data_type, property_types, active, internal, created_on)
	VALUES (NEXT VALUE FOR quality_type_seq, 'Bauplatz', 'type', 'bool', 'G', 1, 1, CURRENT_TIMESTAMP);
INSERT INTO quality_type (id, name, category, data_type, property_types, active, internal, created_on)
	VALUES (NEXT VALUE FOR quality_type_seq, 'Garten', 'type', 'bool', 'G', 1, 1, CURRENT_TIMESTAMP);
INSERT INTO quality_type (id, name, category, data_type, property_types, active, internal, created_on)
	VALUES (NEXT VALUE FOR quality_type_seq, 'Sonstiges Grundstück', 'type', 'bool', 'G', 1, 1, CURRENT_TIMESTAMP);
	
INSERT INTO quality_type (id, name, category, data_type, property_types, active, internal, created_on)
	VALUES (NEXT VALUE FOR quality_type_seq, 'Gäste-WC', 'bath', 'bool', 'W', 1, 1, CURRENT_TIMESTAMP);
INSERT INTO quality_type (id, name, category, data_type, property_types, active, internal, created_on)
	VALUES (NEXT VALUE FOR quality_type_seq, 'Bad mit Fenster', 'bath', 'bool', 'W', 1, 1, CURRENT_TIMESTAMP);
INSERT INTO quality_type (id, name, category, data_type, property_types, active, internal, created_on)
	VALUES (NEXT VALUE FOR quality_type_seq, 'Badewanne', 'bath', 'bool', 'WH', 1, 1, CURRENT_TIMESTAMP);
INSERT INTO quality_type (id, name, category, data_type, property_types, active, internal, created_on)
	VALUES (NEXT VALUE FOR quality_type_seq, 'Dusche', 'bath', 'bool', 'WH', 1, 1, CURRENT_TIMESTAMP);

INSERT INTO quality_type (id, name, category, data_type, property_types, active, internal, created_on)
	VALUES (NEXT VALUE FOR quality_type_seq, 'Sat Anlage', 'equip', 'bool', 'WHB', 1, 1, CURRENT_TIMESTAMP);
INSERT INTO quality_type (id, name, category, data_type, property_types, active, internal, created_on)
	VALUES (NEXT VALUE FOR quality_type_seq, 'Kabelanschluss', 'equip', 'bool', 'WHB', 1, 1, CURRENT_TIMESTAMP);
INSERT INTO quality_type (id, name, category, data_type, property_types, active, internal, created_on)
	VALUES (NEXT VALUE FOR quality_type_seq, 'Aufzug', 'equip', 'bool', 'WHB', 1, 1, CURRENT_TIMESTAMP);
INSERT INTO quality_type (id, name, category, data_type, property_types, active, internal, created_on)
	VALUES (NEXT VALUE FOR quality_type_seq, 'Hausmeisterservice', 'equip', 'bool', 'WHB', 1, 1, CURRENT_TIMESTAMP);
INSERT INTO quality_type (id, name, category, data_type, property_types, active, internal, created_on)
	VALUES (NEXT VALUE FOR quality_type_seq, 'Anz. Balkone', 'equip', 'num', 'WHB', 1, 1, CURRENT_TIMESTAMP);
INSERT INTO quality_type (id, name, category, data_type, property_types, active, internal, created_on)
	VALUES (NEXT VALUE FOR quality_type_seq, 'Anz. Terrassen', 'equip', 'num', 'WHB', 1, 1, CURRENT_TIMESTAMP);

INSERT INTO quality_type (id, name, category, data_type, property_types, active, internal, created_on)
	VALUES (NEXT VALUE FOR quality_type_seq, 'Garage', 'car', 'bool', 'WHBS', 1, 1, CURRENT_TIMESTAMP);
INSERT INTO quality_type (id, name, category, data_type, property_types, active, internal, created_on)
	VALUES (NEXT VALUE FOR quality_type_seq, 'Doppelgarage', 'car', 'bool', 'WHBS', 1, 1, CURRENT_TIMESTAMP);
INSERT INTO quality_type (id, name, category, data_type, property_types, active, internal, created_on)
	VALUES (NEXT VALUE FOR quality_type_seq, 'TG-Stellplatz', 'car', 'bool', 'WHBS', 1, 1, CURRENT_TIMESTAMP);
INSERT INTO quality_type (id, name, category, data_type, property_types, active, internal, created_on)
	VALUES (NEXT VALUE FOR quality_type_seq, 'KFZ-Außenstellplatz', 'car', 'bool', 'WHBS', 1, 1, CURRENT_TIMESTAMP);
INSERT INTO quality_type (id, name, category, data_type, property_types, active, internal, created_on)
	VALUES (NEXT VALUE FOR quality_type_seq, 'Carport', 'car', 'bool', 'WHBS', 1, 1, CURRENT_TIMESTAMP);

INSERT INTO quality_type (id, name, category, data_type, property_types, active, internal, created_on)
	VALUES (NEXT VALUE FOR quality_type_seq, 'Gas-Zentralheizung', 'heat', 'bool', 'WHB', 1, 1, CURRENT_TIMESTAMP);
INSERT INTO quality_type (id, name, category, data_type, property_types, active, internal, created_on)
	VALUES (NEXT VALUE FOR quality_type_seq, 'Öl-Zentralheizung', 'heat', 'bool', 'WHB', 1, 1, CURRENT_TIMESTAMP);
INSERT INTO quality_type (id, name, category, data_type, property_types, active, internal, created_on)
	VALUES (NEXT VALUE FOR quality_type_seq, 'Fernwärme', 'heat', 'bool', 'WHB', 1, 1, CURRENT_TIMESTAMP);
INSERT INTO quality_type (id, name, category, data_type, property_types, active, internal, created_on)
	VALUES (NEXT VALUE FOR quality_type_seq, 'Nachtspeicherheizung', 'heat', 'bool', 'WHB', 1, 1, CURRENT_TIMESTAMP);
INSERT INTO quality_type (id, name, category, data_type, property_types, active, internal, created_on)
	VALUES (NEXT VALUE FOR quality_type_seq, 'Elektroheizung', 'heat', 'bool', 'WHB', 1, 1, CURRENT_TIMESTAMP);
INSERT INTO quality_type (id, name, category, data_type, property_types, active, internal, created_on)
	VALUES (NEXT VALUE FOR quality_type_seq, 'Fußbodenheizung', 'heat', 'bool', 'WHB', 1, 1, CURRENT_TIMESTAMP);
INSERT INTO quality_type (id, name, category, data_type, property_types, active, internal, created_on)
	VALUES (NEXT VALUE FOR quality_type_seq, 'Pelletheizung', 'heat', 'bool', 'WHB', 1, 1, CURRENT_TIMESTAMP);
INSERT INTO quality_type (id, name, category, data_type, property_types, active, internal, created_on)
	VALUES (NEXT VALUE FOR quality_type_seq, 'Wärmepumpe', 'heat', 'bool', 'WHB', 1, 1, CURRENT_TIMESTAMP);
INSERT INTO quality_type (id, name, category, data_type, property_types, active, internal, created_on)
	VALUES (NEXT VALUE FOR quality_type_seq, 'Wärmetauscher', 'heat', 'bool', 'WHB', 1, 1, CURRENT_TIMESTAMP);
INSERT INTO quality_type (id, name, category, data_type, property_types, active, internal, created_on)
	VALUES (NEXT VALUE FOR quality_type_seq, 'Holzöfen', 'heat', 'bool', 'WHB', 1, 1, CURRENT_TIMESTAMP);
INSERT INTO quality_type (id, name, category, data_type, property_types, active, internal, created_on)
	VALUES (NEXT VALUE FOR quality_type_seq, 'Warmluftheizung', 'heat', 'bool', 'WHB', 1, 1, CURRENT_TIMESTAMP);
INSERT INTO quality_type (id, name, category, data_type, property_types, active, internal, created_on)
	VALUES (NEXT VALUE FOR quality_type_seq, 'Offener Kamin', 'heat', 'bool', 'WHB', 1, 1, CURRENT_TIMESTAMP);

INSERT INTO quality_type (id, name, category, data_type, property_types, active, internal, created_on)
	VALUES (NEXT VALUE FOR quality_type_seq, 'neuwertig', 'state', 'bool', 'WHB', 1, 1, CURRENT_TIMESTAMP);
INSERT INTO quality_type (id, name, category, data_type, property_types, active, internal, created_on)
	VALUES (NEXT VALUE FOR quality_type_seq, 'gepflegt', 'state', 'bool', 'WHB', 1, 1, CURRENT_TIMESTAMP);
INSERT INTO quality_type (id, name, category, data_type, property_types, active, internal, created_on)
	VALUES (NEXT VALUE FOR quality_type_seq, 'renoviert', 'state', 'bool', 'WHB', 1, 1, CURRENT_TIMESTAMP);
INSERT INTO quality_type (id, name, category, data_type, property_types, active, internal, created_on)
	VALUES (NEXT VALUE FOR quality_type_seq, 'saniert', 'state', 'bool', 'WHB', 1, 1, CURRENT_TIMESTAMP);

COMMIT;