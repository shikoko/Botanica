class CreateLocations < ActiveRecord::Migration
  def change
    create_table :locations do |t|
      t.decimal :lat, precision: 9, scale: 6
      t.decimal :lng, precision: 9, scale: 6
      t.references :plant, index: true, foreign_key: true
      t.text :description

      t.timestamps null: false
    end
  end
end
