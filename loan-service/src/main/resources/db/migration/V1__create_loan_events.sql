

CREATE TABLE loan_events (
    id BIGSERIAL PRIMARY KEY,
    loan_id UUID NOT NULL,
    version INT NOT NULL,
    event_type VARCHAR(50) NOT NULL,
    amount_cents BIGINT NOT NULL,
    occurred_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);


ALTER TABLE loan_events ADD CONSTRAINT uq_loan_events_loan_version UNIQUE (loan_id, version);


