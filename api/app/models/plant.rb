class Plant < ActiveRecord::Base
  has_many :locations, dependent: :destroy

  acts_as_mappable through: :locations
end
