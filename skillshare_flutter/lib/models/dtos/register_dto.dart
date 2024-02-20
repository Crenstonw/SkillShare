class RegisterDto {
  String? email;
  String? name;
  String? surname;
  String? password;

  RegisterDto({this.email, this.name, this.surname, this.password});

  RegisterDto.fromJson(Map<String, dynamic> json) {
    email = json['email'];
    name = json['name'];
    surname = json['surname'];
    password = json['password'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['email'] = this.email;
    data['name'] = this.name;
    data['surname'] = this.surname;
    data['password'] = this.password;
    return data;
  }
}
