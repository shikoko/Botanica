class API::PlantsController < ApplicationController
  def create
    plants = Plant.all

    plants = plants.joins('LEFT JOIN locations ON locations.plant_id = plants.id')

    query_string = query_string_from_params!
    location = location_from_params!
    distance = distance_from_params!
    limit = limit_from_params!

    if query_string.present?
      fields = [:name, :description]
      plants = plants.where((fields.map { |field| "plants.#{field} LIKE ?" }).join(' OR '), *fields.map { |_| "%#{query_string}%" })
    end

    if distance.present?
      plants = plants.within(distance, origin: location)
    else
      plants = plants.by_distance(origin: location)
    end

    plants = plants.limit(limit) if limit.present?

    response_success(plants)
  end

  private

  def location_from_params!
    { lat: params![:latitude], lng: params![:longitude] }.values
  end

  def limit_from_params!
    params![:limit]
  end

  def distance_from_params!
    params![:distance]
  end

  def query_string_from_params!
    params![:queryString]
  end
end
