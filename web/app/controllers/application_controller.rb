class ApplicationController < ActionController::Base
  attr_accessor :json_post_params

  before_filter :parse_params

  attr_accessor :current_user

  before_filter :authenticate

  INVALID_REQUEST = 1
  INVALID_EMAIL = 2

  protected

  def response_success(payload)
    render json: { error: 0, payload: JSON.parse(render_to_string(json: payload)) }
  end

  def response_error(code)
    render json: { error: code }
  end

  alias_method :params!, :json_post_params

  private

  def parse_params
    @json_post_params = JSON.parse(request.raw_post, symbolize_names: true)
    p ['REQUEST', @json_post_params]
  rescue JSON::ParserError => _
    response_error(INVALID_REQUEST)
  end

  def authenticate
    response_error(INVALID_EMAIL) if params![:email].blank?
    @current_user = User.find_or_create_by!(email: params![:email])
  end
end
