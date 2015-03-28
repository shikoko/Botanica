# This file should contain all the record creation needed to seed the database with its default values.
# The data can then be loaded with the rake db:seed (or created alongside the db with db:setup).
#
# Examples:
#
#   cities = City.create([{ name: 'Chicago' }, { name: 'Copenhagen' }])
#   Mayor.create(name: 'Emanuel', city: cities.first)

require 'csv'

def clean(value)
  (value || '').strip
end

Plant.destroy_all

# one = Plant.create!(name: 'Menta', bothanical: 'Mentha', description: 'Description', uses: 'Uses', parts: 'Parts', picture: 'https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcRSl1WjBB20c0ax1euhXbo_ntbU_ghnT-AApLtK0jqQCYenwWNAUvrh_W8k')
# two = Plant.create!(name: 'Cannabis', bothanical: 'Cannabis', description: 'Description', uses: 'Uses', parts: 'Parts', picture: 'https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcRSl1WjBB20c0ax1euhXbo_ntbU_ghnT-AApLtK0jqQCYenwWNAUvrh_W8k')

CSV.foreach('db/db.csv', headers: :first_row) do |row|
  plant = Plant.new

  plant.name = clean(row['Denumire populara'])
  plant.bothanical = clean(row['Denumire stiintifica'])
  plant.description = clean(row['Descriere'])
  plant.uses = clean(row['Proprietati / utilizare'])
  plant.parts = clean(row['Partile folosite'])
  plant.picture = clean(row['Imagini']).split(/\s/).first

  plant.save!

  plant.locations.create!(lat: 47.659321 + Random.rand, lng: 23.57102851 + Random.rand, description: 'description')
end

plants = Plant.limit(2)

one = plants[0]
two = plants[1]

one_location = one.locations.create!(lat: 47.6595501, lng: 23.5805274, description: 'description')
two_location = two.locations.create!(lat: 47.6595601, lng: 23.5805774, description: 'description')

one_location.pictures.create!(url: 'http://i.imgur.com/GnbGUNy.jpg')
two_location.pictures.create!(url: 'http://i.imgur.com/GnbGUNy.jpg')
