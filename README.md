# Pizzeria - BACK Dalis
<i>Atkreipkite dėmesį, kad projektas, kuriame tai skaitote, yra tik galinė dalis!
jums taip pat reikės front-end dalies. Nuoroda pateikta po šią pastabą ↓</i><br/>
<a href="https://github.com/Vlinkus/BaigiamasisDarbas_PicerijaFront">Frontas</a>

[***Readme in english***](README_EN.md)

# Turinys

- [**Įvadas**](#įvadas)
    - [Kūrėjai](#kūrėjai)
    - [Stack'as](#stackas)
- [**Serverio paleidimas**](#serverio-paleidimas)
    - [Saugykla](#saugykla)
- [**Serverio veikimas**](#serverio-veikimas)
    - [API komandos](#api-komandos)
        - [Swagger 3 - OpenAPI 3](#swagger-3---openapi-3)
        - [Autentikacija ir autorizacija](#autentikacija-ir-autorizacija)
            - [Registracija](#registracija)
            - [Prisijungimas](#prisijungimas)
            - [Atsijungimas](#atsijungimas)
            - [Refresh tokenas](#refresh-tokenas)

# Įvadas

<p>Šiame baigiamajame projekte pateikiamas picerijos restorano REST serveris arba backendas. 
Dėl <i>"React"</i> priekinės dalies galite spustelėti 
<a href="https://github.com/Vlinkus/BaigiamasisDarbas_PicerijaFront">šią nuorodą.</a></p>

## Kūrėjai

Šį projektą vykdė 3 dalyviai (vienas iš jų turėjo dvi paskyras😂):

<a href="https://github.com/Vlinkus/BaigiamasisDarbas_Picerija/graphs/contributors">
    <img src="https://contrib.rocks/image?repo=Vlinkus/BaigiamasisDarbas_Picerija" width="40%"/>
</a>

Šis serveris apsaugotas nuo XSS (Cross Site Scripting) atakų ir jame įdiegta CORS (Cross-origin resource sharing)
saugiam bendravimui tarp front'o ir back'o.
## Stack'as

Stack'as panaudotas šiame REST serveryje:
- **Java 17**
- **Spring Framework Stack'as:**
    - Spring Boot 3.1.2
    - Spring Boot Data JPA
    - Spring Boot Web
    - Spring Boot Security
    - Spring Boot Validation
    - Spring Boot Test
- **Doumenų bazė:**
    - MySQL
    - H2 *(testavimui)*
- **API Dokumentacijos Stack'as:**
    - Swagger UI 3
    - OpenAPI 3
- **Kitos išorinės bibliotekos:**
    - Lombok *(šabloniniam kodui generuoti)*
    - Mockito *(testaviui)*
    - Spring Security Test *(skirtas su saugumu susijusiems testavimams)*

# Serverio paleidimas

Prieš tęsdami įsitikinkite, kad jūsų sistemoje įdiegta bent ***JDK 17*** versijos.

## Saugykla

Norėdami gauti šią saugyklą, tiesiog nueikite į vietinį aplanką
kuriame norite ją saugoti, ir paleiskite šią komandą:

```shell
git clone https://github.com/Vlinkus/BaigiamasisDarbas_Picerija.git
```

Taip pat yra daug daugiau būdų, kaip atsiųsti šį projektą.
Projekto "GitHub" saugykloje paspauskite mygtuką "*code*", kad gautumėte papildomų pasirinkimų.

Po greito įdiegimo galėsite naudoti savo kodo redaktorių(*Eclipse*, *Intellij IDEA*...)

# Serverio veikimas

## API komandos

Projekto portas pagal nutylėjimą  nustatytas kaip 8080.
Visose su api susijusiose nuorodose šiuose poskyriuose bus naudojamas anksčiau minėtas portas.

### Swagger 3 - OpenAPI 3

Šis projektas paremtas "OpenAPI 3" dokumentacija.
Jei norite išsamiai išanalizuoti galinius taškus ir sužinoti visus galimus atsakymus, antraštes ir slapukus,
maloniai prašome apsilankyti numatytojo swagger-ui puslapyje: http://localhost:8080/swagger-ui/index.html

### Autentikacija ir autorizacija

Norėdami pradėti, turite susikurti paskyrą.
Jei naudojate tik REST serverį,
geriausias pasirinkimas bus Postman arba bet kuri kita alternatyva.

#### Registracija
Paskyros registravimas yra gana paprastas procesas:
- Nustatyti *HTTP* užklausą į ``POST``
- Nustatyti adresą ``localhost:8080/api/v1/auth/register``
- Siųskite *JSON* kūną, kaip žemiau pateiktame pavyzdyje

```json
{
    "firstname": "Vardenis",
    "lastname": "Pavardenis",
    "username": "vartotojoVardas",
    "email": "kaz@kas.lt",
    "password": "slaptazodis",
    "role": "USER"
}
```
Svarbu paminėti, kad laukas "*role*" nėra privalomas ir siuntėjas gali jo nenurodyti,
tokiu atveju numatytasis registruojamo naudotojo vaidmuo bus "USER".

Visi galimi vaidmenų(*rolių*) variantai: `USER`, `MANAGER`, `ADMIN`

#### Prisijungimas
Prisijungimui reikia tik dviejų laukų.
- Nustatyti *HTTP* užklausą į ``POST``
- Nustatyti adresą ``localhost:8080/api/v1/auth/login``

```json
{
    "username": "someUsername",
    "password": "password"
}
```
Sėkmingai patvirtinus autentiškumą, gausite ***JWT prieigos žetoną***(refresh token),
naudotojo ***role*** ir ***HttpOnly atnaujinimo žetono slapuką***(HttpOnly refresh token cookie).

#### Atsijungimas
Atsijungimas yra paprastas procesas:

- Turėkite galiojantį ``HttpOnly JWT refresh token'ą``.
- Turėkite galiojantį ``JWT access token`` - pasirinktinai.
- Nustatyti *HTTP* užklausą į ``GET``
- Nustatyti adresą ``localhost:8080/api/v1/auth/logout``

Atsijungimo API leidžia naudotojams nutraukti sesiją ir panaikinti prieigos žetoną (*access token*), jei jis yra.
Tai užkerta kelią abiejų žetonų naudojimui tolesnėms autorizacijos užklausoms.

#### Refresh tokenas
Prieigos žetono (*access token*) atnaujinimas yra žetonais pagrįsto autentifikavimo srauto dalis.
JWT prieigos žetonas veikia trumpai, todėl jį reikia atnaujinti naudojant ***atnaujinimo žetono***(refresh token):

- Turėkite galiojantį ``HttpOnly JWT refresh token'ą``.
- Nustatyti *HTTP* užklausą į ``GET``
- Nustatyti adresą ``localhost:8080/api/v1/auth/refresh-token``

Atnaujinimo žetono API leidžia naudotojams gauti naują prieigos žetoną naudojant galiojantį atnaujinimo žetoną.
Tai padeda pratęsti sesijos trukmę nereikalaujant, kad naudotojas iš naujo įvestų savo prisijungimo duomenis.
