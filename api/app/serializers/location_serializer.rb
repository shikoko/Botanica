class LocationSerializer < ActiveModel::Serializer
  attributes :lat, :lng
  attributes :description

  has_many :pictures
end
