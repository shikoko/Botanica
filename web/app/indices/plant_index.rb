ThinkingSphinx::Index.define :plant, with: :active_record do
  # fields
  indexes name, sortable: true
  indexes bothanical
  indexes description
  indexes uses
  indexes parts

  # attributes
  has created_at, updated_at

  has 'RADIANS(`locations`.`lat`)', as: :latitude, type: :float
  has 'RADIANS(`locations`.`lng`)', as: :longitude, type: :float

  join 'LEFT OUTER JOIN `locations` ON `locations`.`plant_id` = `plants`.`id`'
end
