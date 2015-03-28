class CreatePlants < ActiveRecord::Migration
  def change
    create_table :plants do |t|
      t.string :name
      t.string :bothanical
      t.text :description
      t.text :uses
      t.text :parts
      t.string :picture

      t.timestamps null: false
    end
  end
end
