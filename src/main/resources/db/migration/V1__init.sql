SET search_path TO public;

CREATE TABLE IF NOT EXISTS resource (
  id         BIGINT GENERATED BY DEFAULT AS IDENTITY,
  tenant     VARCHAR(255) NOT NULL,
  field_1    VARCHAR(255) NOT NULL,
  field_2    VARCHAR(255) NOT NULL,
  field_3    VARCHAR(255) NOT NULL,
  created_by VARCHAR(255) NOT NULL,
  created_at TIMESTAMP(6) WITH TIME ZONE NOT NULL,
  PRIMARY KEY (id, tenant)
);