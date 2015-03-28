class Location < ActiveRecord::Base
  acts_as_mappable

  belongs_to :plant

  has_many :pictures, as: :imageable, dependent: :destroy
end
