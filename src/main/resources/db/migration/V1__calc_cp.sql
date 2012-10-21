/* Root table for project data. */
CREATE TABLE project
(
  id serial NOT NULL,
  name character varying(50) NOT NULL,
  description character varying(255),
  CONSTRAINT project_pk PRIMARY KEY (id ),
  CONSTRAINT project_name_uq UNIQUE (name )
);

/* Unique ID for a calculation result. */
CREATE TABLE calculation
(
  id bigserial NOT NULL,
  project_id integer,
  filename text,
  CONSTRAINT calculation_pk PRIMARY KEY (id ),
  CONSTRAINT calc_proj_fk FOREIGN KEY (project_id)
      REFERENCES project (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT calc_file_proj UNIQUE (project_id , filename )
);

CREATE INDEX fki_calc_proj_fk
  ON calculation
  USING btree
  (project_id );

/* Stores calculation summary data. */
CREATE TABLE calc_summary
(
  solvent_type character varying(50),
  stoichiometry character varying(50),
  charge integer,
  multiplicity integer,
  functional character varying(50),
  basis_set character varying(50),
  energy double precision,
  dipole double precision,
  zpe double precision,
  h298 double precision,
  g298 double precision,
  freq1 double precision,
  freq2 double precision,
  calc_id bigint NOT NULL,
  CONSTRAINT calc_summary_pk PRIMARY KEY (calc_id ),
  CONSTRAINT calc_summary_fk FOREIGN KEY (calc_id)
      REFERENCES calculation (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

/* Cremer Pople calculation storage. */

CREATE TABLE cremer_pople
(
  calc_id bigint NOT NULL,
  phi double precision,
  theta double precision,
  q double precision,
  pucker character varying(3),
  r1 double precision,
  r2 double precision,
  r3 double precision,
  r4 double precision,
  r5 double precision,
  r6 double precision,
  o1 double precision,
  o2 double precision,
  o3 double precision,
  o4 double precision,
  o6 double precision,
  CONSTRAINT cremer_pople_pk PRIMARY KEY (calc_id ),
  CONSTRAINT cremer_pople_calc_id FOREIGN KEY (calc_id)
      REFERENCES calculation (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

/* Categories for calculations. */
CREATE TABLE category
(
  id serial NOT NULL,
  name character varying(50) NOT NULL,
  description text,
  CONSTRAINT category_pk PRIMARY KEY (id ),
  CONSTRAINT category_name_uq UNIQUE (name )
);

/* Lookup table mapping calculations with categories. */
CREATE TABLE calc_category
(
  calc_id bigint,
  cat_id integer,
  CONSTRAINT calc_cat_calc_fk FOREIGN KEY (calc_id)
      REFERENCES calculation (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT calc_cat_cat_fk FOREIGN KEY (cat_id)
      REFERENCES category (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT calc_cat_uq UNIQUE (calc_id , cat_id )
);
