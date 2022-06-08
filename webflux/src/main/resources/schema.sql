CREATE TABLE IF NOT EXISTS nace (
    id INTEGER PRIMARY KEY,
    level_value INTEGER,
    code VARCHAR(16),
    parent VARCHAR(16),
    description TEXT,
    this_item_includes TEXT,
    this_item_also_includes TEXT,
    rulings TEXT,
    this_item_excludes TEXT,
    reference_to_ISIC VARCHAR(16)
);
