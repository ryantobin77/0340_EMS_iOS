from django.shortcuts import render
from django.http import HttpResponse
from django.http import JsonResponse
from . import models

import json
from .models import Hospital, SpecialtyCenter, Diversion

# Create your views here.
def home(request):
    return HttpResponse('<h1>Welcome to the EMS Mobile Backend!</h1>')


#currently just updates the data in the database whenever the server is launched
#could be updated to be inside a method where requests to update the data can be made from the app
Hospital.objects.all().delete()
for line in open('../../scrapers/georgiarcc.json', 'r'):
    hosp = json.loads(line)
    obj = Hospital(name=hosp['Hospital'],street=hosp['Street'],city=hosp['City'],county=hosp['County'],state='GA',zip=hosp['Zip'],phone=hosp['Phone Number'],rch=hosp['RegCoord'],ems_region=hosp['EMSRegion'],nedocs_score=hosp['Nedocs'],last_updated=hosp['Updated'])
    obj.save()
    for spec in hosp['Specialty center']:
        obj.specialty_center.add(spec)
    for div in hosp['Status']:
        try:
            obj.diversions.add(div)
        except:
            Diversion.objects.create(type=div)
            obj.diversions.add(div)


def get_hospitals(request):
    hospitals_list = list()
    hospitals = models.Hospital.objects.all()
    for hospital in hospitals:
        specialty_centers = hospital.specialty_center.all()
        specialty_centers_list = list()
        for spec in specialty_centers:
            specialty_centers_list.append(str(spec))

        diversions = hospital.diversions.all()
        diversions_list = list()
        for div in diversions:
            diversions_list.append(str(div))
        next = {
            'name' : hospital.name,
            'street' : hospital.street,
            'city' : hospital.city,
            'county' : hospital.county,
            'state' : hospital.state,
            'zip' : hospital.zip,
            'phone' : hospital.phone,
            'rch' : hospital.rch,
            'ems_region' : hospital.ems_region,
            'specialty_centers' : specialty_centers_list,
            'last_updated' : hospital.last_updated,
            'nedocs_score' : hospital.nedocs_score,
            'diversions' : diversions_list,

        }
        hospitals_list.append(next)
    return JsonResponse(hospitals_list, safe=False)
