class PlantSerializer < ActiveModel::Serializer
  attributes :name, :description, :picture
  has_many :locations
end
