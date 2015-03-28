class API::PlantsController < ApplicationController
  def create
    query_string = query_string_from_params!
    location = location_from_params!
    distance = distance_from_params!
    limit = limit_from_params!

    # if params[:experimental] != '1'
      response_success(experimental(query_string, location, distance, limit))
    # else
    #   response_success(old(query_string, location, distance, limit))
    # end
  end

  private

  def location_from_params!
    { lat: payload[:latitude], lng: payload[:longitude] }.values
  end

  def limit_from_params!
    payload[:limit]
  end

  def distance_from_params!
    payload[:distance]
  end

  def query_string_from_params!
    payload[:queryString] || ''
  end

  def experimental(query_string, location, distance, limit)
    conditions = { }

    if limit.present?
      conditions[:page] = 1
      conditions[:per_page] = limit
    end

    if location.present?
      conditions[:geo] = [location[0] * Math::PI / 180, location[1] * Math::PI / 180]

      if distance.present?
        conditions[:with] = { geodist: 0.0..distance.to_f }
      end

      conditions[:order] = 'geodist ASC'
    end

    Plant.search(query_string, conditions)
  end

  def old(query_string, location, distance, limit)
    plants = Plant.all

    plants = plants.eager_load(:locations)
    plants = plants.eager_load(locations: :pictures)

    if query_string.present?
      fields = [:name, :bothanical, :uses, :parts, :description]
      plants = plants.where((fields.map { |field| "plants.#{field} LIKE ?" }).join(' OR '), *fields.map { |_| "%#{query_string}%" })
    end

    if distance.present?
      plants = plants.within(distance, origin: location)
    else
      plants = plants.by_distance(origin: location, reverse: false)
    end

    plants = plants.limit(limit) if limit.present?

    plants
  end
end
