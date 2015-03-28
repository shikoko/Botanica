ThinkingSphinx::Index.define :plant, with: :active_record do
  # fields
  indexes name, sortable: true
  indexes bothanical
  indexes description
  indexes uses
  indexes parts

  # attributes
  has created_at, updated_at
end
