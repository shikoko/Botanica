class PlantSerializer < ActiveModel::Serializer
  attributes :name, :bothanical, :uses, :parts, :description, :picture
  has_many :locations
end
