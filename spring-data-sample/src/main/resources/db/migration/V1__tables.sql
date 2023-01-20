CREATE TABLE foo (
    id UUID PRIMARY KEY,
    created_date TIMESTAMP WITH TIME ZONE NOT NULL,
    modified_date TIMESTAMP WITH TIME ZONE NOT NULL,
    is_deleted BOOLEAN NOT NULL,
    name VARCHAR(128) NOT NULL,
    data JSONB NOT NULL
);
CREATE INDEX foo_createddate_idx ON foo(created_date);
CREATE INDEX foo_modified_date_idx ON foo(modified_date);
CREATE INDEX foo_name_idx ON foo(name);

CREATE TABLE foo_child (
    id UUID PRIMARY KEY,
    name VARCHAR(128) NOT NULL,
    field VARCHAR(128) NOT NULL,
    foo_id UUID REFERENCES foo(id) ON DELETE CASCADE NOT NULL
);
CREATE INDEX foochild_fooid_idx ON foo_child(foo_id);

CREATE TABLE bar (
    id UUID PRIMARY KEY,
    created_date TIMESTAMP WITH TIME ZONE NOT NULL,
    modified_date TIMESTAMP WITH TIME ZONE NOT NULL,
    is_deleted BOOLEAN NOT NULL,
    name VARCHAR(128) NOT NULL,
    foo_id UUID REFERENCES foo(id) ON DELETE CASCADE NOT NULL
);
CREATE INDEX bar_createddate_idx ON bar(created_date);
CREATE INDEX bar_modifieddate_idx ON bar(modified_date);
CREATE INDEX bar_name_idx ON bar(name);
CREATE INDEX bar_fooid_idx ON bar(foo_id);
