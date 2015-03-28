class Location < ActiveRecord::Base
  acts_as_mappable default_units: :kms

  belongs_to :plant

  has_many :pictures, as: :imageable, dependent: :destroy
end
