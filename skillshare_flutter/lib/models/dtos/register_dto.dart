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
    final Map<String, dynamic> data = <String, dynamic>{};
    data['email'] = email;
    data['name'] = name;
    data['surname'] = surname;
    data['password'] = password;
    return data;
  }
}
